package com.example.scientificcalculator.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.horizontalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import net.objecthunter.exp4j.ExpressionBuilder

@Composable
fun MainScreen(
    onItemClick: (NavKey) -> Unit = {},
    modifier: Modifier = Modifier
) {
    var expression by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    var lastResult by remember { mutableStateOf("") } // Remembers the last answer for chaining

    val history = remember { mutableStateListOf<Pair<String, String>>() }
    var showHistory by remember { mutableStateOf(false) }

    val operators = listOf("/", "*", "-", "+", "^")

    val onAction: (String) -> Unit = { action ->
        when (action) {
            "C" -> {
                expression = ""
                result = ""
            }
            "DEL" -> {
                if (expression.isNotEmpty()) {
                    expression = expression.dropLast(1)
                    // Clear result preview when editing
                    result = ""
                }
            }
            "=" -> {
                if (expression.isNotEmpty()) {
                    try {
                        val e = ExpressionBuilder(expression).build()
                        val eval = e.evaluate()
                        var res = eval.toString()
                        if (res.endsWith(".0")) {
                            res = res.dropLast(2)
                        }
                        // Commit to history tape and clear display
                        history.add(expression to res)
                        lastResult = res
                        expression = ""
                        result = ""
                    } catch (ex: Exception) {
                        result = "Error"
                    }
                }
            }
            "sin", "cos", "tan", "sqrt" -> {
                expression += "$action("
            }
            "1/x" -> {
                val valueToInvert = if (expression.isNotEmpty()) expression else lastResult
                if (valueToInvert.isNotEmpty()) {
                    try {
                        val expr = "1/($valueToInvert)"
                        val e = ExpressionBuilder(expr).build()
                        val eval = e.evaluate()
                        var res = eval.toString()
                        if (res.endsWith(".0")) {
                            res = res.dropLast(2)
                        }
                        history.add(expr to res)
                        lastResult = res
                        expression = ""
                        result = ""
                    } catch (ex: Exception) {
                        result = "Error"
                    }
                }
            }
            "ln" -> {
                expression += "log("
            }
            "log" -> {
                expression += "log10("
            }
            in operators -> {
                // If expression is empty but we have a previous result, chain from it
                if (expression.isEmpty() && lastResult.isNotEmpty()) {
                    expression = lastResult + action
                } else if (expression.isNotEmpty()) {
                    expression += action
                }
            }
            else -> {
                expression += action
            }
        }
    }

    // Dynamic font size — shrinks as expression gets longer
    val textSize by animateFloatAsState(
        targetValue = when {
            expression.length > 40 -> 22f
            expression.length > 25 -> 28f
            else -> 36f
        },
        animationSpec = tween(300, easing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)),
        label = "textSize"
    )

    // Dynamic font size for result — shrinks for long results
    val resultSize by animateFloatAsState(
        targetValue = when {
            result.length > 16 -> 28f
            result.length > 12 -> 34f
            result.length > 8 -> 40f
            else -> 48f
        },
        animationSpec = tween(300, easing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)),
        label = "resultSize"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
                .padding(top = 24.dp, bottom = 8.dp)
        ) {
            // ============================================================
            // TOP SECTION: Display area — scrollable calculation tape
            // Shows past calculations + current input, auto-scrolls down
            // ============================================================
            val listState = rememberLazyListState()

            // Auto-scroll to bottom whenever history or expression changes
            LaunchedEffect(history.size, expression) {
                // Scroll to last item (current input is always at the end)
                val totalItems = history.size + 1 // history items + current input
                if (totalItems > 0) {
                    listState.animateScrollToItem(totalItems - 1)
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Takes ALL leftover space, keyboard is fixed below
            ) {
                // History button — floated top-right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 8.dp)
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.05f))
                        .clickable { showHistory = true },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "H",
                        color = Color.White.copy(alpha = 0.7f),
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                // Calculation tape
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 4.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    // Past calculations
                    items(history) { (expr, res) ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = expr,
                                color = Color.White.copy(alpha = 0.85f),
                                fontSize = 20.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Text(
                                text = "= $res",
                                color = Color(0xFF4CAF50),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Light,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }

                    // Current input — always at bottom
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp, bottom = 12.dp),
                            horizontalAlignment = Alignment.End
                        ) {
                            // Expression text
                            Text(
                                text = expression.ifEmpty { "0" },
                                color = if (expression.isEmpty()) Color.White.copy(alpha = 0.3f)
                                        else Color.White.copy(alpha = 0.9f),
                                fontSize = textSize.sp,
                                textAlign = TextAlign.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                maxLines = 3
                            )

                            // Result text
                            if (result.isNotEmpty()) {
                                Text(
                                    text = result,
                                    color = Color(0xFF4CAF50),
                                    fontSize = resultSize.sp,
                                    fontWeight = FontWeight.Light,
                                    textAlign = TextAlign.End,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 4.dp),
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }

            // ============================================================
            // BOTTOM SECTION: Button grid — FIXED HEIGHT, never changes
            // 7 rows × 56dp per row + spacing = always the same size
            // ============================================================
            val buttons = listOf(
                listOf("sin", "cos", "tan", "log"),
                listOf("ln", "sqrt", "(", ")"),
                listOf("C", "DEL", "^", "/"),
                listOf("7", "8", "9", "*"),
                listOf("4", "5", "6", "-"),
                listOf("1", "2", "3", "+"),
                listOf(".", "0", "1/x", "=")
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                buttons.forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp), // FIXED row height — never changes
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        row.forEach { btn ->
                            CalculatorButton(
                                text = btn,
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight(),
                                onClick = { onAction(btn) }
                            )
                        }
                    }
                }
            }
        }

        // History Overlay
        AnimatedVisibility(
            visible = showHistory,
            enter = fadeIn(tween(400)) + slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(600, easing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f))
            ),
            exit = fadeOut(tween(300)) + slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(500, easing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f))
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF050505).copy(alpha = 0.9f))
                    .clickable { showHistory = false }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    Text(
                        text = "History",
                        color = Color.White,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier.padding(top = 32.dp, bottom = 24.dp)
                    )

                    if (history.isEmpty()) {
                        Text(
                            text = "No calculations yet.",
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(history.reversed()) { (expr, res) ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(16.dp))
                                        .background(Color.White.copy(alpha = 0.05f))
                                        .clickable {
                                            expression = expr
                                            result = res
                                            showHistory = false
                                        }
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        text = expr,
                                        color = Color.Gray,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = res,
                                        color = Color.White,
                                        fontSize = 28.sp,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.End,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isOperator = text in listOf("/", "*", "-", "+", "^")
    val isControl = text in listOf("C", "DEL")
    val isAction = text == "="

    // Aesthetic base colors for the Double-Bezel
    val outerColor = when {
        isAction -> Color(0xFF1B5E20)
        isControl -> Color(0xFFb71c1c)
        isOperator -> Color(0xFF1A237E)
        text in listOf("sin", "cos", "tan", "log", "ln", "sqrt", "(", ")", "1/x") -> Color(0xFF263238)
        else -> Color(0xFF1A1A1A)
    }

    val innerColorStart = when {
        isAction -> Color(0xFF4CAF50).copy(alpha = 0.3f)
        isControl -> Color(0xFFE53935).copy(alpha = 0.3f)
        isOperator -> Color(0xFF3949AB).copy(alpha = 0.3f)
        else -> Color.White.copy(alpha = 0.1f)
    }

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Magnetic scale animation
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.93f else 1f,
        animationSpec = tween(400, easing = CubicBezierEasing(0.32f, 0.72f, 0f, 1f)),
        label = "buttonScale"
    )

    // Outer container — fills its cell, centers the circle inside
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Outer Shell (Doppelrand technique) — perfect circle constrained by height
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f) // Perfect circle: width = height
                .scale(scale)
                .background(outerColor, CircleShape)
                .shadow(
                    elevation = 8.dp,
                    shape = CircleShape,
                    spotColor = Color.Black.copy(alpha = 0.5f)
                )
                .padding(2.dp) // creates the hairline outer border
        ) {
            // Inner Core
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                innerColorStart,
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.4f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .clip(CircleShape)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null // Custom indication via scale
                    ) { onClick() }
            ) {
                Text(
                    text = text,
                    color = if (isAction || isControl || isOperator) Color.White else Color.White.copy(alpha = 0.85f),
                    fontSize = if (text.length > 2) 16.sp else 24.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

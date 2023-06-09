package com.example.restaurants_tips

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.restaurants_tips.ui.theme.Restaurants_tipsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Restaurants_tipsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RestaurantTipCalculator()
                }
            }
        }
    }
}


@Composable
fun RestaurantTipCalculator2() {
    var billAmount by remember { mutableStateOf("") }
    var tipPercentage by rememberSaveable { mutableStateOf(18f) }
    val focusRequester = FocusRequester()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Restaurant Tip Calculator") },
                backgroundColor = MaterialTheme.colors.primary
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            ) {
                OutlinedTextField(
                    value = billAmount,
                    onValueChange = { billAmount = it },
                    label = { Text("Bill Amount") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)
                        .onFocusChanged { focusState ->
                            if (focusState.isFocused) {
                                focusRequester.requestFocus()
                            }
                        }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Tip Percentage: $tipPercentage%")
                Spacer(modifier = Modifier.height(8.dp))
                Slider(
                    value = tipPercentage,
                    onValueChange = { tipPercentage = it },
                    valueRange = 0f..30f,
//                    steps = 100,
                    modifier = Modifier.fillMaxWidth()
                )
                it
            }
        }
    )
}

@Composable
fun RestaurantTipCalculator() {
    InsertAmount()
}

@Composable
fun InsertAmount() {
    var billAmount by rememberSaveable { mutableStateOf("") }
    var tipPercentage by rememberSaveable { mutableStateOf(18f) }


    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(115.dp)
                .padding(horizontal = 16.dp, vertical = 15.dp)
        ) {
            Text(
                text = "Amount",
                fontSize = 23.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 32.dp)
            )

            Box(
                Modifier
                    .width(270.dp)
                    .padding(start = 8.dp, top = 8.dp, bottom = 8.dp)
            ) {
                OutlinedTextField(
                    value = billAmount,
                    onValueChange = { billAmount = it },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    modifier = Modifier
                        .fillMaxWidth()

                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Custom %",
                fontSize = 20.sp,
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 9.dp)
            )
            Slider(
                value = tipPercentage,
                onValueChange = { tipPercentage = it },
                valueRange = 0f..30f,
                modifier = Modifier.width(250.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            ThreeColumnsAndRows(
                calculateTip(billAmount, tipPercentage.toString()),
                tipPercentage,
                billAmount
            )
        }
    }
}

fun formatDecimal(value: String): String {
    var cleanString = value.replace(Regex("[^\\d]"), "")
    val decimalIndex = cleanString.length - 2
    if (decimalIndex >= 1) {
        cleanString =
            cleanString.substring(0, decimalIndex) + "." + cleanString.substring(decimalIndex)
    }
    if (cleanString.length > 5) {
        cleanString = cleanString.substring(0, cleanString.length - 5) + "." +
                cleanString.substring(cleanString.length - 5, cleanString.length - 3) + "." +
                cleanString.substring(cleanString.length - 3)
    }
    return cleanString
}


@Composable
fun ThreeColumnsAndRows(tip: String, percent: Float, amount: String) {

    Column(Modifier.fillMaxSize()) {
        Row() {
            Text(
                text = " ", modifier = Modifier
                    .weight(1.0f)
            )
            Text(
                text = "15%",
                modifier = Modifier
                    .weight(1.0f)
                    .border(2.dp, color = Color.White),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,

                )
            Text(
                text = "${percent.toInt()}%",
                modifier = Modifier
                    .weight(1.0f)
                    .border(2.dp, color = Color.White),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
            )

        }
        Row() {
            Text(
                text = "Tip:  ",
                modifier = Modifier
                    .weight(1.0f) ,
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.End,
            )
            Text(
                text = "${calculateTipDefaultPercent(amount)} R$",
                modifier = Modifier
                    .weight(1.0f)
                    .background(Color.LightGray)
                    .border(2.dp, color = Color.White),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "$tip RS", modifier = Modifier
                    .weight(1.0f)
                    .background(Color.LightGray)
                    .border(2.dp, color = Color.White),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
            )

        }
        Row() {
            Text(
                text = "Total:  ", modifier = Modifier
                    .weight(1.0f),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.End,
            )
            Text(
                text = "${totalValue(calculateTipDefaultPercent(amount), amount)}",
                modifier = Modifier
                    .weight(1.0f)
                    .background(Color.LightGray)
                    .border(2.dp, color = Color.White),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
            )
            Text(
                text = totalValue(calculateTip(amount, percent.toString()), amount),
                modifier = Modifier
                    .weight(1.0f)
                    .background(Color.LightGray)
                    .border(2.dp, color = Color.White),
                style = MaterialTheme.typography.subtitle1,
                textAlign = TextAlign.Center,
            )

        }

    }
}

fun calculateTip(billAmount: String, tipPercentage: String): String {
    val amount = billAmount.toFloatOrNull() ?: 0f
    val percent = tipPercentage.toFloatOrNull() ?: 0f
    val total = amount * percent.toInt() / 100f
    return "%.2f".format(total)
}

fun calculateTipDefaultPercent(billAmount: String): String {
    val amount = billAmount.toFloatOrNull() ?: 0f
    val total = (amount * 1.15f) - amount
    return "%.2f".format(total)
}

fun totalValue(percent: String, amount: String): String {
    println("tip$percent")
    val newTip = percent.replace(',', '.')
    val amountFloat = amount.toFloatOrNull() ?: 0f
    val percentTotal = newTip.toFloatOrNull() ?: 0f
    val total = amountFloat + percentTotal
    println("amount $amountFloat")
    println("tip$percentTotal")
    return "%.2f".format(total)
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Restaurants_tipsTheme {
        RestaurantTipCalculator()
    }
}
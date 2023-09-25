package com.example.mobilefactory.feature

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mobilefactory.R
import com.example.mobilefactory.ui.theme.MobileFactoryTheme

@Composable
fun RegistrationRoute(
    registrationScreenViewModel: RegistrationScreenViewModel = hiltViewModel(),
) {
    val uiState by registrationScreenViewModel.uiState.collectAsStateWithLifecycle()
    val specificTime by registrationScreenViewModel.specificTime.collectAsStateWithLifecycle()
    val startTime by registrationScreenViewModel.startTime.collectAsStateWithLifecycle()
    val endingTime by registrationScreenViewModel.endingTime.collectAsStateWithLifecycle()

    RegistrationScreen(
        uiState = uiState,
        specificTime = specificTime,
        setSpecificTime = registrationScreenViewModel::setSpecificTime,
        startTime = startTime,
        setStartTime = registrationScreenViewModel::setStartTime,
        endingTime = endingTime,
        setEndingTime = registrationScreenViewModel::setEndingTime,
        registerTime = registrationScreenViewModel::registerTime,
    )
}

@Composable
internal fun RegistrationScreen(
 uiState: RegistrationUiState,
 specificTime: String,
 setSpecificTime: (String) -> Unit,
 startTime: String,
 setStartTime: (String) -> Unit,
 endingTime: String,
 setEndingTime: (String) -> Unit,
 registerTime: () -> Unit,
) {
    Column(
        modifier = Modifier.padding(all = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val baseModifier = Modifier.fillMaxWidth()
        Picker(
            modifier = baseModifier,
            specificTime = specificTime,
            setSpecificTime = setSpecificTime,
            startTime = startTime,
            setStartTime = setStartTime,
            endingTime = endingTime,
            setEndingTime = setEndingTime,
        )
        RegistrationButton(
            modifier = baseModifier,
            registerTime = registerTime
        )
        ResultDisplay(
            modifier = baseModifier,
            uiState = uiState
        )
    }
}

@Composable
private fun Picker(
    modifier: Modifier = Modifier,
    specificTime: String,
    setSpecificTime: (String) -> Unit,
    startTime: String,
    setStartTime: (String) -> Unit,
    endingTime: String,
    setEndingTime: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val pattern = remember { Regex("^[0-9]{1,2}\$") }
        val midnightZero = 0
        val midnightTwentyFour = 24
        val textCountTwo = 2
        val firstText = 1
        val zeroText = "0"
        OutlinedTextField(
            value = specificTime,
            onValueChange = {
                if (
                    it != "" &&
                    (!it.matches(pattern) ||
                            (it.toInt() < midnightZero || midnightTwentyFour < it.toInt()))
                ) return@OutlinedTextField

                val text =
                    if (it.count() == textCountTwo && it.startsWith(zeroText)) {
                        it.drop(firstText)
                    } else {
                        it
                    }
                setSpecificTime(text)
            },
            modifier = Modifier.width(100.dp),
            label = { Text(text = stringResource(id = R.string.time)) },
            suffix = { Text(text = stringResource(id = R.string.suffix_time)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Column {
            OutlinedTextField(
                value = startTime,
                onValueChange = {
                    if (
                        it != "" &&
                        (!it.matches(pattern) ||
                                (it.toInt() < 0 || 24 < it.toInt()))
                    ) return@OutlinedTextField

                    val text =
                        if (it.count() == textCountTwo && it.startsWith(zeroText)) {
                            it.drop(firstText)
                        } else {
                            it
                        }
                    setStartTime(text)
                },
                modifier = Modifier.width(100.dp),
                label = { Text(text = stringResource(id = R.string.start_time)) },
                suffix = { Text(text = stringResource(id = R.string.suffix_time)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(
                value = endingTime,
                onValueChange = {
                    if (
                        it != "" &&
                        (!it.matches(pattern) ||
                                (it.toInt() < 0 || 24 < it.toInt()))
                    ) return@OutlinedTextField

                    val text =
                        if (it.count() == textCountTwo && it.startsWith(zeroText)) {
                            it.drop(firstText)
                        } else {
                            it
                        }
                    setEndingTime(text)
                },
                modifier = Modifier.width(100.dp),
                label = { Text(text = stringResource(id = R.string.ending_time)) },
                suffix = { Text(text = stringResource(id = R.string.suffix_time)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
private fun RegistrationButton(
    modifier: Modifier = Modifier,
    registerTime: () -> Unit,
) {
    Button(
        onClick = { registerTime() },
        modifier = modifier
    ) {
        Text(text = stringResource(id = R.string.registration))
    }
}

@Composable
private fun ResultDisplay(
    modifier: Modifier = Modifier,
    uiState: RegistrationUiState
) {
    LazyColumn(
        modifier = modifier.fillMaxHeight()
    ) {
        when (uiState) {
            is RegistrationUiState.Loading ->
                item {
                    Text(text = stringResource(id = R.string.loading_data))
                }
            is RegistrationUiState.Empty ->
                item {
                    Text(text = stringResource(id = R.string.no_data))
                }
            is RegistrationUiState.Time -> {
                val data = uiState.time
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(id = R.string.table_start_time),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.table_ending_time),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.table_time),
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = stringResource(id = R.string.table_result),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
                items(data.size) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = data[it].startTime,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = data[it].endingTime,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = data[it].registeredTime,
                            modifier = Modifier.weight(1f)
                        )
                        Text(
                            text = if (data[it].result) {
                                stringResource(id = R.string.table_result_in)
                            } else {
                                stringResource(id = R.string.table_result_out)
                            },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MobileFactoryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            RegistrationRoute()
        }
    }
}
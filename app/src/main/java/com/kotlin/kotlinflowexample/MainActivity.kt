package com.kotlin.kotlinflowexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.kotlin.kotlinflowexample.ui.MainViewModel
import com.kotlin.kotlinflowexample.ui.theme.KotlinFlowExampleTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        collectLatestLifecycleFlow(viewModel.stateFlow) {number ->
            val n = number.toString()
        }

        setContent {
            KotlinFlowExampleTheme {
                val viewModel = viewModel<MainViewModel>()
//                val time = viewModel.countDown.collectAsState(initial = 10)
                val count = viewModel.stateFlow.collectAsState(0)
                var number = 1;
                // A surface container using the 'background' color from the theme
                Box(
                    modifier = Modifier.fillMaxSize()
//                    color = MaterialTheme.colorScheme.background
                ) {
//                    Text(
//                        text = time.value.toString(),
//                        fontSize = 30.sp,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
                    Row() {
                        Button(onClick = { viewModel.increment() }) {
                            Text(text = "Counter: ${count.value}")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KotlinFlowExampleTheme {
        Greeting("Android")
    }
}

fun <T> ComponentActivity.collectLatestLifecycleFlow(flow: Flow<T>, collect:suspend (T) -> Unit){

    lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED){
            flow.collectLatest(collect)
        }
    }
}
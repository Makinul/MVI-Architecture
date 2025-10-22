package com.makinul.mvi.architecture.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.camera2.interop.ExperimentalCamera2Interop
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.allHorizontalHingeBounds
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.HorizontalRuler
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.LookaheadScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import com.makinul.mvi.architecture.ui.theme.MVIArchitectureTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


val HorizontalHingeTopRuler = HorizontalRuler()
val HorizontalHingeBottomRuler = HorizontalRuler()

@ExperimentalCamera2Interop
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVIArchitectureTheme {
                val horizontalHinge = currentWindowAdaptiveInfo().windowPosture
                    .allHorizontalHingeBounds.firstOrNull()
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    CameraPreviewScreen(viewModel)
//                }
                Box(
                    Modifier.layout { measurable, constraints ->
                        val placeable = measurable.measure(constraints)
                        layout(
                            width = constraints.maxWidth,
                            height = constraints.maxHeight,
                            rulers = {
                                if (horizontalHinge != null) {
                                    val bounds = coordinates.windowToLocal(horizontalHinge)
                                    HorizontalHingeTopRuler provides bounds.top
                                    HorizontalHingeBottomRuler provides bounds.bottom
                                }
                            }
                        ) { placeable.place(0, 0) }
                    }
                ) {
                    CameraPreviewScreen(viewModel)
                }
            }
        }
    }
}

private fun LayoutCoordinates.windowToLocal(rect: Rect): Rect =
    Rect(
        topLeft = windowToLocal(rect.topLeft),
        bottomRight = windowToLocal(rect.bottomRight),
    )

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
    MVIArchitectureTheme {
        Greeting("Android")
    }
}
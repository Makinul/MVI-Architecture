package com.makinul.mvi.architecture.ui

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker
import com.makinul.mvi.architecture.ui.theme.MVIArchitectureTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MVIArchitectureTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CameraPreview { imageProxy ->
//                        analyzeImage(imageProxy, landmarker)
                        Log.d("MainActivity", "Image analyzed")
                    }
                }
            }
        }
    }
}

@Composable
fun CameraPreview(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA,
    onFrameAnalysis: (ImageProxy) -> Unit // Your frame processing callback
) {
    val context = LocalContext.current
    val previewView = remember { PreviewView(context) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    // Use a SideEffect or LaunchedEffect to configure and bind CameraX
    LaunchedEffect(cameraProviderFuture) {
        val cameraProvider = cameraProviderFuture.get()
        val preview = Preview.Builder().build().apply {
            surfaceProvider = previewView.surfaceProvider
        }

        val imageAnalyzer = ImageAnalysis.Builder()
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()
            .apply {
                // Set your custom analyzer here
                setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                    onFrameAnalysis(imageProxy)
                }
            }

        try {
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                lifecycleOwner,
                cameraSelector,
                preview,
                imageAnalyzer // Crucial for MediaPipe
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    // Display the PreviewView
    AndroidView(
        factory = { previewView },
        modifier = Modifier.fillMaxSize()
    )
}

//// Example of the frame analysis function
//fun analyzeImage(imageProxy: ImageProxy, landmarker: FaceLandmarker) {
//    val bitmap = imageProxy.toBitmap() // Utility function needed
//    // Apply rotation/flip transformation to the bitmap for correct orientation
//    val rotatedBitmap =
//        applyTransformations(bitmap, imageProxy.imageInfo.rotationDegrees, isFrontCamera)
//
//    val mpImage = BitmapImageBuilder(rotatedBitmap).build()
//    val frameTime = SystemClock.uptimeMillis()
//
//    landmarker.detectAsync(mpImage, frameTime)
//
//    // IMPORTANT: Close the ImageProxy to release the buffer
//    imageProxy.close()
//}
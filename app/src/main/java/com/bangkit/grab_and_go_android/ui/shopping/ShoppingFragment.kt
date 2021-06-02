package com.bangkit.grab_and_go_android.ui.shopping

import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.databinding.FragmentShoppingBinding
import com.bangkit.grab_and_go_android.utils.toastLong
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


@androidx.camera.core.ExperimentalGetImage
class ShoppingFragment : Fragment() {

    private lateinit var binding: FragmentShoppingBinding
//    private val viewModel by viewModels<ShoppingViewModel>()

    private lateinit var container: LinearLayout
    private lateinit var imageCapture: ImageCapture
    private lateinit var preview: Preview
    private lateinit var camera: Camera
    private lateinit var cameraProvider: ProcessCameraProvider
    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    private fun navController(): NavController {
        return Navigation.findNavController(requireActivity(), R.id.nav_host)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoppingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view as LinearLayout

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        setUpCamera()
        updateUi()
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        val previewView = binding.viewFinder

        cameraProviderFuture.addListener(Runnable {
            // Camera provider is now guaranteed to be available
            cameraProvider = cameraProviderFuture.get()

            // Choose the camera by requiring a lens facing
            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            // Set up the preview use case to display camera preview.
            preview = Preview.Builder().build()

            // Set up the capture use case to allow users to take photos.
            imageCapture = ImageCapture.Builder()
                .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                .build()

            // Must unbind the use-cases before rebinding them
            cameraProvider.unbindAll()

            try {
                // Attach use cases to the camera with the same lifecycle owner
                camera = cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)

                // Connect the preview use case to the previewView
                preview.setSurfaceProvider(previewView.surfaceProvider)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }

        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun updateUi() {
        binding.btnBack.setOnClickListener {
            navController().navigateUp()
        }
//        binding.btnCart.setOnClickListener {
//            navController().navigate(ShoppingFragmentDirections.actionShoppingFragmentToShoppingCartFragment())
//        }
        // Listener for button used to capture photo
        binding.btnCapture.setOnClickListener {
            // Get a stable reference of the modifiable image capture use case
            imageCapture.let { imageCapture ->
                // Setup image capture listener which is triggered after photo has been taken
                imageCapture.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
                        override fun onError(exc: ImageCaptureException) {
                            Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                        }
                        override fun onCaptureSuccess(image: ImageProxy) {
                            super.onCaptureSuccess(image)
//                            val img1 = image.image?.planes
//                            val img2 = image.planes
                            Log.d(TAG, image.imageInfo.toString())
                            Log.d(TAG, "format1: "+image.image?.format.toString())
                            Log.d(TAG, "width= ${image.width}, height = ${image.height}")
                            if(image.format == ImageFormat.JPEG) {
                                val bundle = Bundle().apply {
                                    Log.d(TAG, Thread.currentThread().toString())
                                    val buffer: ByteBuffer = image.planes[0].buffer
                                    val bytes = ByteArray(buffer.capacity())
                                    buffer.get(bytes)
                                    putByteArray(PicturePreviewFragment.IMAGE_BYTES_ARG, bytes)
                                    putInt(PicturePreviewFragment.IMAGE_WIDTH_ARG, image.width)
                                    putInt(PicturePreviewFragment.IMAGE_HEIGHT_ARG, image.height)
                                    putInt(PicturePreviewFragment.IMAGE_ROTATION_ARG, image.imageInfo.rotationDegrees)
                                }
                                Log.d(TAG, Thread.currentThread().toString())
                                lifecycleScope.launchWhenStarted {
                                    Navigation.findNavController(requireActivity(), R.id.nav_host).navigate(
                                        R.id.action_shoppingFragment_to_picturePreviewFragment,
                                        bundle
                                    )
                                }
                            } else {
                                toastLong("Image format for this device is not JPEG")
                            }

                        }
                    })

                // We can only change the foreground Drawable using API level 23+ API
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Display flash animation to indicate that photo was captured
                    container.postDelayed({
                        container.foreground = ColorDrawable(Color.WHITE)
                        container.postDelayed(
                            { container.foreground = null }, ANIMATION_FAST_MILLIS)
                    }, ANIMATION_SLOW_MILLIS)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state.
        if (!CameraPermissionsFragment.hasPermissions(requireContext())) {
            navController().navigate(
                ShoppingFragmentDirections.actionShoppingFragmentToPermissions()
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Shut down our background executor
        cameraExecutor.shutdown()
    }

    companion object {
        const val TAG = "ShoppingFragment"
        /** Milliseconds used for UI animations */
        const val ANIMATION_FAST_MILLIS = 50L
        const val ANIMATION_SLOW_MILLIS = 100L
    }

}
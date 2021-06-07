package com.bangkit.grab_and_go_android.ui.camera

import android.graphics.Color
import android.graphics.ImageFormat
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bangkit.grab_and_go_android.R
import com.bangkit.grab_and_go_android.data.Cart
import com.bangkit.grab_and_go_android.databinding.FragmentCameraBinding
import com.bangkit.grab_and_go_android.ui.cart.CartFragment
import com.bangkit.grab_and_go_android.utils.YuvToRgbConverter
import com.bangkit.grab_and_go_android.utils.setUpProgressBar
import com.bangkit.grab_and_go_android.utils.toastLong
import dagger.hilt.android.AndroidEntryPoint
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@AndroidEntryPoint
@androidx.camera.core.ExperimentalGetImage
class CameraFragment : Fragment() {

    //    private lateinit var bitmapBuffer: Bitmap
    private lateinit var byteArray: ByteArray
    private lateinit var binding: FragmentCameraBinding
    private val viewModel by viewModels<CameraViewModel>()
    private lateinit var container: RelativeLayout
    private lateinit var imageCapture: ImageCapture
    private lateinit var preview: Preview
    private lateinit var camera: Camera
    private lateinit var cameraProvider: ProcessCameraProvider
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK

//    private var goToPreviewState: Boolean = false
    private var bundle: Bundle = Bundle()

    /** Blocking camera operations are performed using this executor */
    private val cameraExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    private fun navController(): NavController {
        return Navigation.findNavController(requireActivity(), R.id.nav_host)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpProgressBar(binding.cameraProgressBar, viewModel.loading)
        container = view as RelativeLayout
        // Initialize our background executor
//        cameraExecutor = Executors.newSingleThreadExecutor()

//        viewModel.previewState.observe(viewLifecycleOwner, { isTrue ->
//            if (isTrue) {
//                goToPreview()
//            }
//        })
//        viewModel.cartNavState.observe(viewLifecycleOwner, { isTrue ->
//            if(isTrue) {
//                goToCartFragment()
//            }
//        })

        viewModel.cart.observe(viewLifecycleOwner, { cart ->
            if(cart != null) {
                goToCartFragment(cart)
            }
        })

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
        // Listener for button used to capture photo
        binding.btnCapture.setOnClickListener {
            // Get a stable reference of the modifiable image capture use case
            imageCapture.let { imageCapture ->
                // Setup image capture listener which is triggered after photo has been taken
                imageCapture.takePicture(cameraExecutor, object : ImageCapture.OnImageCapturedCallback() {
                        override fun onError(e: ImageCaptureException) {
                            Log.e(TAG, "Photo capture failed: ${e.message}", e)
                        }
                        override fun onCaptureSuccess(image: ImageProxy) {
                            viewModel.setLoading()
                            super.onCaptureSuccess(image)
//                            val imageData = ImageData(
//                                width = image.width,
//                                height = image.height,
//                                imageFormat1 = image.format,
//                                imageFormat2 = image.image?.format ?: -1,
//                                imagePlanes1Size = image.planes.size,
//                                imagePlanes2Size = image.image?.planes?.size ?: -1,
//                                rotationDegree = image.imageInfo.rotationDegrees,
//                                timestamp = image.imageInfo.timestamp,
//                                cropRectWidth = image.cropRect.width(),
//                                cropRectHeight = image.cropRect.height()
//                            )
                            when (image.format) {
                                ImageFormat.JPEG -> {
                                    val buffer: ByteBuffer = image.planes[0].buffer
                                    byteArray = ByteArray(buffer.capacity())
                                    buffer.get(byteArray)
                                }
                                ImageFormat.YUV_420_888 -> {
                                    val converter = YuvToRgbConverter(requireActivity())
//                                    val bitmapBuffer = Bitmap.createBitmap(
//                                        image.width, image.height, Bitmap.Config.ARGB_8888
//                                    )
                                    byteArray = converter.getBytesBuffer(image.image!!)
                                    image.use { converter.imageToByteArray(image.image!!, byteArray) }
//                                    val stream = ByteArrayOutputStream()
//                                    bitmapBuffer.compress(Bitmap.CompressFormat.JPEG, 100, stream)
//                                    val byteArray: ByteArray = stream.toByteArray()
                                }
                                else -> {
                                    toastLong("Error: The image format from camera is not JPEG OR YUV")
                                    return
                                }
                            }

                            val encodedImg: String = Base64.encodeToString(byteArray, Base64.DEFAULT)
//                            val encodedImg: String = ""
//                            Log.d(TAG, "Base64"+encodedImg.subSequence(0, 100))
                            viewModel.getResult(encodedImg)

//                            bundle = Bundle().apply {
//                                putParcelable(PicturePreviewFragment.IMAGE_DATA_ARG, imageData)
//                                putByteArray(PicturePreviewFragment.IMAGE_BYTE_ARRAY_ARG, byteArray)
//                            }
//                            viewModel.goToPreview(true)
//                            viewModel.goToCartFragment(true)

                        }
                    })

                // We can only change the foreground Drawable using API level 23+ API
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Display flash animation to indicate that photo was captured
                    container.postDelayed({
                        container.foreground = ColorDrawable(Color.WHITE)
                        container.postDelayed(
                            { container.foreground = null }, ANIMATION_FAST_MILLIS
                        )
                    }, ANIMATION_SLOW_MILLIS)
                }
            }
        }
    }

    private fun processImage() {

    }

    private fun goToPreview() {
//        lifecycleScope.launchWhenStarted {
            findNavController().navigate(
                R.id.action_cameraFragment_to_picturePreviewFragment,
                bundle
            )
        viewModel.goToPreview(false)
//        }
    }

    private fun goToCartFragment(cart: Cart) {
        val bundle = Bundle().apply {
            putParcelable(CartFragment.CART_ARG, cart)
        }
        findNavController().navigate(
            R.id.action_cameraFragment_to_cartFragment,
            bundle
        )
//        viewModel.goToCartFragment(false)
    }

    override fun onResume() {
        super.onResume()
        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state.
        if (!CameraPermissionsHelper.hasPermissions(requireContext())) {
            navController().navigate(
                CameraFragmentDirections.actionCameraFragmentToPermissions()
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
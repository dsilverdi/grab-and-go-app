package com.bangkit.grab_and_go_android.ui.shopping

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bangkit.grab_and_go_android.databinding.FragmentPicturePreviewBinding
import com.bumptech.glide.Glide


class PicturePreviewFragment : Fragment() {

    companion object {
        const val IMAGE_BYTES_ARG = "img"
        const val IMAGE_WIDTH_ARG = "width"
        const val IMAGE_HEIGHT_ARG = "height"
        const val IMAGE_ROTATION_ARG = "rotation_degree"
    }

    private lateinit var binding: FragmentPicturePreviewBinding
//    private val args: PicturePreviewFragmentArgs by navArgs()
    private var bytes: ByteArray? = null
    private var width: Int = 0
    private var height: Int = 0
    private var rotationDegree: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            bytes = it.getByteArray(IMAGE_BYTES_ARG)
            width = it.getInt(IMAGE_WIDTH_ARG)
            height = it.getInt(IMAGE_HEIGHT_ARG)
            rotationDegree = it.getInt(IMAGE_ROTATION_ARG)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPicturePreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val img: ByteArray = args.image
        bytes?.let {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes!!.size, null)
            Glide.with(view).load(bitmap).into(binding.img)
        }
//        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)
        binding.tvImgWidth.text = "width: $width"
        binding.tvImgHeight.text = "height: $height"
        binding.tvImgDegreee.text = "rotationDegree: $rotationDegree"

    }
}
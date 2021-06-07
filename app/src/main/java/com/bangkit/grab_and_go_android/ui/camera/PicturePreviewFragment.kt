package com.bangkit.grab_and_go_android.ui.camera

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
        const val IMAGE_DATA_ARG = "img_data"
        const val IMAGE_BYTE_ARRAY_ARG = "img"
    }

    private lateinit var binding: FragmentPicturePreviewBinding
//    private val args: PicturePreviewFragmentArgs by navArgs()
    private var imageData: ImageData? = null
    private var byteArray: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            imageData = it.getParcelable(IMAGE_DATA_ARG)
            byteArray = it.getByteArray(IMAGE_BYTE_ARRAY_ARG)
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
        byteArray?.let {
            val bitmap: Bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray!!.size, null)
            Glide.with(view).load(bitmap).into(binding.img)
            binding.tvImgInfo.text = "size = ~${(it.size/1024)}KB"
        }

//        val bitmap = BitmapFactory.decodeByteArray(image, 0, image.size)

        imageData?.let {
            binding.tvImgClass.text = it.toString()
        }

    }
}
package com.example.hello

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.hello.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val helloTextView = view.findViewById(R.id.hello_text) as TextView
        val argName = arguments?.getString("name")
        val argLang = arguments?.get("language") as Language

        val translation = argLang.translate()
        val image = argLang.image()

        /* Generate message ("Hello + name!" or just "Hello!" if name is empty */
        var helloText: String = translation!!
        if (argName != "") {
            helloText += " $argName"
        }
        helloText += "!"
        helloTextView.text = helloText

        /* Set background image (corresponds to country selected) */
        val imageView = view.findViewById(R.id.background_imageview) as ImageView
        imageView.setImageResource(image!!)

        binding.buttonSecond.setOnClickListener {
            val bundle = bundleOf(
                Pair("name", argName),
                Pair("language", argLang))
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
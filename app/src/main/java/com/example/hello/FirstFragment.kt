package com.example.hello

//import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.core.view.updateLayoutParams
import androidx.navigation.fragment.findNavController
import androidx.transition.Scene
import com.example.hello.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private var languageSelected: Language = Language.Select
    private var expand: Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = view.findViewById<EditText>(R.id.name_input)

        /* Restore state if Back button has been pressed from second fragment */
        val argName = arguments?.getString("name")
        val argLang = arguments?.get("language") as Language
        if (argName != null) {
            nameEditText.setText(argName)
        }
        if (argLang != Language.Select) {
            languageSelected = argLang
            val languageSelectBtn = view.findViewById<Button>(R.id.select_btn)
            val lightFont = context?.let { ResourcesCompat.getFont(it, R.font.cera_pro_light) }
            languageSelectBtn.text = languageSelected.name
            languageSelectBtn.typeface = lightFont
        }

        /* When button clicked, navigate to second fragment and pass input data */
        binding.buttonFirst.setOnClickListener {
            val nameText: String = nameEditText.text.toString()
            val bundle = bundleOf(
                Pair("name", nameText),
                Pair("language", languageSelected))
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment, bundle)
        }

        languageSelectSetup(view, savedInstanceState)
    }

    private fun languageSelectSetup(view: View, savedInstanceState: Bundle?) {
        val languageSelectBtn = view.findViewById(R.id.select_btn) as Button
        val languageScrollView = view.findViewById(R.id.language_scroll_view) as ScrollView
        val languageContainer = view.findViewById(R.id.languages_container) as LinearLayout
        val lightFont = context?.let { ResourcesCompat.getFont(it, R.font.cera_pro_light) }
        val boldFont = context?.let { ResourcesCompat.getFont(it, R.font.cera_pro_bold) }
        val languageButtons = ArrayList<Button>(6)
        val chevronIcon = view.findViewById(R.id.drop_down_chevron) as ImageView

        /* Add a button corresponding to each language option */
        Language.values().forEach {
            if (it != Language.Select) {
                val language = it
                val button = Button(this.context)
                button.setBackgroundColor(Color.TRANSPARENT)
                button.text = language.name
                button.textSize = 22.0F
                button.typeface = lightFont
                button.transformationMethod = null
                languageButtons.add(button)
                button.setOnClickListener {
                    languageSelectBtn.text = button.text
                    languageSelectBtn.typeface = lightFont
                    val newHeight = (75 * context?.resources?.displayMetrics?.density!!).toInt()
                    languageScrollView.updateLayoutParams { height = newHeight }
                    languageScrollView.fullScroll(ScrollView.FOCUS_UP)
                    languageSelected = language
                    expand = false
                    languageButtons.forEach { btn ->
                        languageContainer.removeView(btn)
                    }
                }
            }
        }

        /* Expand/collapse when select language button is clicked */
        languageSelectBtn.setOnClickListener {
            expand = !expand
            val newHeightDp: Int
            if (expand) {
                newHeightDp = 220
                languageButtons.forEach { btn ->
                    languageContainer.addView(btn)
                }
                chevronIcon.setImageResource(R.drawable.chevron_up)
            } else {
                newHeightDp = 75
                languageButtons.forEach { btn ->
                    languageContainer.removeView(btn)
                }
                chevronIcon.setImageResource(R.drawable.chevron_down)
            }
            val newHeightPixels = newHeightDp * context?.resources?.displayMetrics?.density!!
            languageScrollView.updateLayoutParams { height = newHeightPixels.toInt() }
            languageScrollView.requestLayout()
            languageSelected = Language.Select
            languageSelectBtn.text = getString(R.string.select_language)
            languageSelectBtn.typeface = boldFont
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
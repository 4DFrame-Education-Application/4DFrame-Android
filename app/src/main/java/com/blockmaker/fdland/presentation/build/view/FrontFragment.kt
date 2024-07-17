package com.blockmaker.fdland.presentation.build.view

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import com.blockmaker.fdland.R
//
//class FrontFragment : Fragment() {
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_build_result_front, container, false)
//    }
//}

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.blockmaker.fdland.R
import org.json.JSONObject

class FrontFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE_URL = "image_url"
        private const val ARG_JSON_RESULTS = "json_results"

        fun newInstance(imageUrl: String, jsonResults: String): FrontFragment {
            val fragment = FrontFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, imageUrl)
            args.putString(ARG_JSON_RESULTS, jsonResults)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_build_result_front, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView: ImageView = view.findViewById(R.id.imageView3)

        val jsonResults = arguments?.getString(ARG_JSON_RESULTS) ?: "{}"
        Log.d("Upload", "JSON Results: $jsonResults") // 전체 JSON 결과를 로그로 출력

        val imageUrl = arguments?.getString(ARG_IMAGE_URL)
        if (imageUrl != null) {
            Glide.with(this).load(imageUrl).into(imageView)
        }

        if (jsonResults.isNotEmpty() && jsonResults != "{}") {
            logResults(jsonResults)
        } else {
            Log.d("Upload", "No valid JSON results to parse.")
        }
    }

    private fun logResults(jsonResults: String) {
        try {
            val jsonObject = JSONObject(jsonResults)
            val resultsArray = jsonObject.getJSONArray("results")
            for (i in 0 until resultsArray.length()) {
                val result = resultsArray.getJSONObject(i)
                val name = result.getString("name")
                val accuracy = result.getString("accuracy")
                val rate = result.getString("rate")

                // rate 값에 따라 하트 텍스트 설정
                val heartText = when (rate) {
                    "perfect" -> "♥♥♥♥♥"
                    "great" -> "♥♥♥♥"
                    "good" -> "♥♥♥"
                    "bad" -> "♥♥"
                    "miss" -> "♥"
                    else -> "♥"
                }

                // 각 결과를 로그로 출력
                Log.d("Upload", "Parsed Result - Name: $name, Accuracy: $accuracy, Rate: $heartText")

//                Log.d("Upload", "Parsed Result - Name: $name, Accuracy: $accuracy, Rate: $rate, Hearts: $heartText")
            }
        } catch (e: Exception) {
            Log.e("Upload", "JSON Parse Error: ", e)
        }
    }
}

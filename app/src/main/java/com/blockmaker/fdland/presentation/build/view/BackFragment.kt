package com.blockmaker.fdland.presentation.build.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.blockmaker.fdland.R
import com.blockmaker.fdland.data.model.BuildResultResponse
import com.blockmaker.fdland.presentation.build.adapter.BuildResultAdapter
import com.google.gson.Gson

class BackFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE_URL = "image_url"
        private const val ARG_JSON_RESULTS = "json_results"

        fun newInstance(imageUrl: String, jsonResults: String): BackFragment {
            val fragment = BackFragment()
            val args = Bundle()
            args.putString(ARG_IMAGE_URL, imageUrl)
            args.putString(ARG_JSON_RESULTS, jsonResults)
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_build_result_back, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView: ImageView = view.findViewById(R.id.imageView3)
        textView = view.findViewById(R.id.result_text)
        recyclerView = view.findViewById(R.id.recyclerView)

        val jsonResults = arguments?.getString(ARG_JSON_RESULTS) ?: "{}"
        Log.d("Upload", "JSON Results: $jsonResults") // 전체 JSON 결과를 로그로 출력

        val imageUrl = arguments?.getString(ARG_IMAGE_URL)
        if (imageUrl != null) {
            Log.d("Glide", "Loading image URL: $imageUrl")
            Glide.with(this).load(imageUrl).into(imageView)
        } else {
            Log.d("Glide", "Image URL is null")
        }

        if (jsonResults.isNotEmpty() && jsonResults != "{}") {
            setRecyclerViewData(jsonResults)
        } else {
            Log.d("Upload", "No valid JSON results to parse.")
        }
    }

    private fun setRecyclerViewData(jsonResults: String) {
        try {
            val gson = Gson()
            val resultResponse = gson.fromJson(jsonResults, BuildResultResponse::class.java)
            val buildResults = resultResponse.results

            // RecyclerView 설정
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = BuildResultAdapter(buildResults)

            // 텍스트 뷰에 결과 설정
            textView.text = getString(R.string.result_text, resultResponse.count)
        } catch (e: Exception) {
            Log.e("Upload", "JSON Parse Error: ", e)
        }
    }
}

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

class UpFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE_URL = "image_url"
        private const val ARG_JSON_RESULTS = "json_results"

        // 새로운 인스턴스를 생성하기 위한 메서드
        fun newInstance(imageUrl: String, jsonResults: String): UpFragment {
            val fragment = UpFragment()
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
        // 레이아웃을 인플레이트하여 뷰를 생성
        return inflater.inflate(R.layout.fragment_build_result_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰 초기화
        val imageView: ImageView = view.findViewById(R.id.imageView3)
        textView = view.findViewById(R.id.result_text)
        recyclerView = view.findViewById(R.id.recyclerView)

        // JSON 결과를 가져와서 로그로 출력
        val jsonResults = arguments?.getString(ARG_JSON_RESULTS) ?: "{}"
        Log.d("Upload", "JSON Results: $jsonResults") // 전체 JSON 결과를 로그로 출력

        // 이미지 URL을 가져와서 Glide로 이미지 로드
        val imageUrl = arguments?.getString(ARG_IMAGE_URL)
        if (imageUrl != null) {
            Log.d("Glide", "Loading image URL: $imageUrl")
            Glide.with(this).load(imageUrl).into(imageView)
        } else {
            Log.d("Glide", "Image URL is null")
        }

        // 유효한 JSON 결과가 있을 때 RecyclerView 데이터 설정
        if (jsonResults.isNotEmpty() && jsonResults != "{}") {
            setRecyclerViewData(jsonResults)
        } else {
            Log.d("Upload", "No valid JSON results to parse.")
        }
    }

    private fun setRecyclerViewData(jsonResults: String) {
        try {
            val gson = Gson()
            // JSON 결과를 파싱하여 BuildResultResponse 객체로 변환
            val resultResponse = gson.fromJson(jsonResults, BuildResultResponse::class.java)
            val buildResults = resultResponse.results.map { result ->
                result.copy(rate = when (result.rate) {
                    "perfect" -> "♥♥♥♥♥"
                    "great" -> "♥♥♥♥"
                    "good" -> "♥♥♥"
                    "bad" -> "♥♥"
                    "miss" -> "♥"
                    else -> "♥"
                })
            }

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

package com.example.mycapstone.ui.bookmark

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mycapstone.PolicyAdapter
import com.example.mycapstone.R
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.BookmarkFragmentBinding
import com.google.firebase.database.*
import timber.log.Timber

class BookmarkFragment : Fragment(), PolicyAdapter.PolicyClickEventListener {
  val viewModel: BookmarkViewModel by viewModels()
  lateinit var binding: BookmarkFragmentBinding
  var policyAdapter: PolicyAdapter = PolicyAdapter(this)

  private lateinit var database: DatabaseReference
  private var dataArrayList: ArrayList<Policy> = ArrayList()

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    binding = DataBindingUtil.inflate(inflater, R.layout.bookmark_fragment, container, false)
    binding.vm = viewModel

    binding.rvPolicy.layoutManager =
      LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    binding.rvPolicy.adapter = policyAdapter

    binding.customToolbar.setOnMenuItemClickListener {
      when (it.itemId) {
        R.id.action_login -> {
          findNavController().navigate(R.id.action_bookmark_fragment_to_login_fragment)
          true
        }
        R.id.action_search -> {
          findNavController().navigate(R.id.action_bookmark_fragment_to_search_fragment)
          true
        }
        else -> false
      }
    }
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    getFirebase()
  }

  private fun getFirebase() {
    database = FirebaseDatabase.getInstance().reference.child("likeList")
    database.addListenerForSingleValueEvent(object : ValueEventListener {
      override fun onCancelled(error: DatabaseError) {
        Timber.e("===lmw firebase db error=== ${error.message}")
      }

      override fun onDataChange(snapshot: DataSnapshot) {
        for (data in snapshot.children) {
          try {
            val test = data.value as HashMap<String, Any>
            val policy = Policy(
              name = test["name"] as String?,
              field = test["field"] as String?,
              period = test["period"] as String?,
              age = test["age"] as String?,
              content = test["content"] as String?,
              education = test["education"] as String?,
              jobState = test["jobState"] as String?,
              url = test["url"] as String?,
            )
            dataArrayList.add(policy)
          } catch (e: Exception) {
            Timber.e("===lmw error=== ${e.localizedMessage}")
          }
        }
        policyAdapter.replaceList(dataArrayList)
      }
    })


  }

  override fun onItemClick(policy: Policy) {
    val action = BookmarkFragmentDirections.actionBookmarkFragmentToPolicyDetailFragment(policy)
    findNavController().navigate(action)
  }

  override fun likeClick(policy: Policy) {
    //여기선 좋아요 버튼 안보이게
  }
}
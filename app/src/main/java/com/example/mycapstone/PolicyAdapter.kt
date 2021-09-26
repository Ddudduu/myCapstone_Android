package com.example.mycapstone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.PolicyItemBinding

class PolicyAdapter(private val listener: PolicyClickEventListener) :
  RecyclerView.Adapter<PolicyAdapter.ViewHolder>() {

  interface PolicyClickEventListener {
    fun onItemClick(policy: Policy)
    fun likeClick(policy: Policy)
  }

  var data = mutableListOf<Policy>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val layoutInflater = LayoutInflater.from(parent.context)
    val binding = DataBindingUtil.inflate<ViewDataBinding>(
      layoutInflater, R.layout.policy_item, parent, false
    )
    return ViewHolder(binding as PolicyItemBinding)
  }

  override fun getItemCount(): Int {
    return data.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  inner class ViewHolder(private val binding: PolicyItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Policy) {
      binding.policyField.text = item.field
      binding.policyName.text = item.name
      binding.policyPeriod.text = item.period
      binding.policyTarget.text = item.age

      //상세 페이지로 이동
      binding.policyName.setOnClickListener {
        listener.onItemClick(item)
      }

      binding.likeButton.setOnClickListener {
        if (binding.likeButton.tag == "unselected") {
          binding.likeButton.apply {
            this.setImageResource(R.drawable.star_filled)
            this.tag = "selected"
          }
        } else {
          binding.likeButton.apply {
            this.setImageResource(R.drawable.star)
            this.tag = "unselected"
          }
        }
        listener.likeClick(item)
      }
    }
  }

  fun replaceList(newData: MutableList<Policy>) {
    data = newData.toMutableList()
    notifyDataSetChanged()
  }
}
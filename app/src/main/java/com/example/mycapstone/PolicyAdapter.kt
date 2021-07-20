package com.example.mycapstone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.PolicyItemBinding

class PolicyAdapter :
    RecyclerView.Adapter<PolicyAdapter.MyViewHolder>() {
    var data = mutableListOf<Policy>()

    // policy_item 그리기
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = PolicyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    // data 개수 반환
    override fun getItemCount(): Int {
        return data.size
    }

    // date 대입
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(data[position])
    }

    // policy_item.xml 의 각 textview 에 대입
    class MyViewHolder(private val binding: PolicyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Policy) {
            binding.policyField.text = item.field
            binding.policyName.text = item.name
            binding.policyPeriod.text = item.period
            binding.policyTarget.text = item.target
        }
    }
}
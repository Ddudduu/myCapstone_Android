package com.example.mycapstone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.PolicyItemBinding

class PolicyAdapter : RecyclerView.Adapter<PolicyAdapter.ViewHolder>() {

    var data = mutableListOf<Policy>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            R.layout.policy_item,
            parent,
            false
        )
        return ViewHolder(binding as PolicyItemBinding)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(private val binding: PolicyItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Policy) {
            binding.policyField.text = item.field
            binding.policyName.text = item.name
            binding.policyPeriod.text = item.period
            binding.policyTarget.text = item.target
        }
    }

    fun replaceList(newData: MutableList<Policy>) {
        data = newData.toMutableList()
        notifyDataSetChanged()
    }
}
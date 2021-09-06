package com.example.mycapstone

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mycapstone.data.Policy
import com.example.mycapstone.databinding.PolicyItemBinding
import com.example.mycapstone.ui.home.HomeFragmentDirections

class PolicyAdapter : RecyclerView.Adapter<PolicyAdapter.ViewHolder>() {

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

      //상세 페이지로 이동
      holder.itemView.setOnClickListener { //            val intent = Intent(holder.itemView?.context, PolicyDetail::class.java)
         //            intent.putExtra("policyItem", data[position])
         //            startActivity(holder.itemView.context, intent, null)

         if (it.context.toString().contains("MainActivity")) {
            val action = HomeFragmentDirections.actionHomeFragmentToPolicyDetailFragment(data[position])
            findNavController(it).navigate(action)
         }
      }
   }

   class ViewHolder(private val binding: PolicyItemBinding) : RecyclerView.ViewHolder(binding.root) {
      fun bind(item: Policy) {
         binding.policyField.text = item.field
         binding.policyName.text = item.name
         binding.policyPeriod.text = item.period
         binding.policyTarget.text = item.age
      }
   }

   fun replaceList(newData: MutableList<Policy>) {
      data = newData.toMutableList()
      notifyDataSetChanged()
   }
}
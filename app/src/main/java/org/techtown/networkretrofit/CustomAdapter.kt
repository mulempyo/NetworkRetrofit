package org.techtown.networkretrofit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.techtown.networkretrofit.databinding.ItemRecyclerBinding

class CustomAdapter: RecyclerView.Adapter<Holder>() {
    var userList: Repository? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemRecyclerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    override fun getItemCount(): Int {
        return userList?.size?:0
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = userList?.get(position)
        holder.setUser(user)
    }
}
class Holder(val binding: ItemRecyclerBinding):RecyclerView.ViewHolder(binding.root){
    fun setUser(user: RepositoryItem?){
        user?.let { user ->
            with(binding) {
                textName.text = user.full_name
                textId.text = user.node_id
                Glide.with(imageAvatar).load(user.owner.avatar_url).into(imageAvatar)
            }
        }
    }

}
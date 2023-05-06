package adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.appsquad.AdminDashboard
import com.example.appsquad.ApplyJobForm
import com.example.appsquad.EditApplication
import com.example.appsquad.R
import database.CompanyDatabase
import database.entities.Application
import database.entities.Job
import database.repositories.CompanyRepository
import database.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobApplicationAdapter: RecyclerView.Adapter<JobApplicationAdapter.ViewHolder>() {
    lateinit var data: List<Application>
    lateinit var context: Context

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvAplNotes: TextView
        var tvAplContact: TextView
        var tvAplUser: TextView
        val btnEdit :Button


        init {
            tvAplNotes = view.findViewById(R.id.tvAplNotes)
            tvAplContact = view.findViewById(R.id.tvAplContact)
            tvAplUser = view.findViewById(R.id.tvAplUser)
            btnEdit = view.findViewById(R.id.btnEditApplication)

        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobApplicationAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.application_item, parent, false)

        return JobApplicationAdapter.ViewHolder(view)
    }


    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repositoryU = UserRepository(CompanyDatabase.getInstance(context))
        CoroutineScope(Dispatchers.IO).launch {
            val  dt = data[position].id?.let { repositoryU.getUserDetail(it.toInt()) }
            if (dt != null) {
                holder.tvAplUser.text = dt.name.toString()
            }
        }

        holder.tvAplNotes.text = data[position].notes.toString()
        holder.tvAplContact.text = data[position].contact.toString()

        holder.btnEdit.setOnClickListener {
            var intent = Intent(context, EditApplication::class.java)
            intent.putExtra("aplId" , data[position].id.toString())
            context.startActivity(intent)
        }








    }

    fun setData(data: List<Application>, context: Context) {
        this.data = data
        this.context = context
        notifyDataSetChanged()
    }
}
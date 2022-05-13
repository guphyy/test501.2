package com.example.test5012

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "ProjectName"
private const val ARG_PARAM2 = "Status"
private const val ARG_PARAM3 = "TeamMembers"
private const val ARG_PARAM4 = "TaskName"

/**
 * A simple [Fragment] subclass.
 * Use the [mainVeiwFrag.newInstance] factory method to
 * create an instance of this fragment.
 */


class mainVeiwFrag : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4 = it.getString(ARG_PARAM4)

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main_veiw,container,false)
        val ProjectNameView = view.findViewById<TextView>(R.id.ProjectName_mainView)
        val workerListView = view.findViewById<TextView>(R.id.TeamMembers_mainView)
        val statusView = view.findViewById<TextView>(R.id.status_mainView)
        val TaskView = view.findViewById<TextView>(R.id.TaskName_mainView)
        ProjectNameView.text = param1
        workerListView.text = param2
        statusView.text = param3
        TaskView.text = param4

        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment mainVeiwFrag.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String,param3: String, param4: String) =
            mainVeiwFrag().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putString(ARG_PARAM1, param3)
                    putString(ARG_PARAM2, param4)
                }
            }
    }
}
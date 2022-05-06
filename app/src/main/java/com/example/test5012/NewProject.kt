package com.example.test5012

class NewProject(var projectName: String, var deadline: String, var task: String, var worker: String, var state: String) {

    fun newProject(projectName: String?, deadline: String?, task: String?, worker: String?, state: String?) {
        this.projectName = projectName!!
        this.deadline = deadline!!
        this.task = task!!
        this.worker = worker!!
        this.state = state!!
    }

    @JvmName("getProjectName1")
    fun getProjectName(): String? {
        return projectName
    }

    @JvmName("setProjectName1")
    fun setProjectName(projectName: String?) {
        this.projectName = projectName!!
    }

    @JvmName("getDeadline1")
    fun getDeadline(): String? {
        return deadline
    }

    @JvmName("setDeadline1")
    fun setDeadline(deadline: String?) {
        this.deadline = deadline!!
    }

    @JvmName("getTask1")
    fun getTask(): String? {
        return task
    }

    @JvmName("setTask1")
    fun setPosition(position: String?) {
        this.task = task!!
    }

    @JvmName("getWorker1")
    fun getWorker(): String? {
        return worker
    }

    @JvmName("setWorker1")
    fun setWorker(position: String?) {
        this.worker = worker!!
    }

    override fun toString(): String {
        return "newProject{" +
                "projectName='" + projectName + '\'' +
                ", deadline='" + deadline + '\'' +
                ", task='" + task + '\'' +
                ", worker='" + worker + '\'' +
                ", state='" + state + '\'' +
                '}'
    }
}
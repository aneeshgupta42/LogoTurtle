package controller;

import java.util.ArrayList;



public class TreeNode {

    private String name;
    private String result;
    private ArrayList<TreeNode> children;

    public TreeNode() {
        children = new ArrayList<>();
    }

    public TreeNode(String name) {
        this();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getResult() {
        return result;
    }

    public TreeNode getChild() {
        return children.get(0);
    }

    public void setResult(String result) {
        this.result = result;
    }

    public ArrayList<TreeNode> getChildren() {
        return  children;
    }

    public void addChild(TreeNode child) {
        children.add((TreeNode) child);
    }

    public int getChildrenNumber(TreeNode child) {
        return children.size();
    }

    public void setData(String data) {
        this.name = data;
    }
}

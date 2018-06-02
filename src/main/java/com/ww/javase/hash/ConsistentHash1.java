package com.ww.javase.hash;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by weiwei on 2018/4/25.
 */
public class ConsistentHash1 {

    public static void main(String[] args) {
        NodeArray nodeArray = new NodeArray();

        Node[] nodes = {
                new Node("Node--> 1"),
                new Node("Node--> 2"),
                new Node("Node--> 3")
        };

        for (Node node : nodes) {
            nodeArray.addNode(node);
        }

        Obj[] objs = {
                new Obj("1"),
                new Obj("2"),
                new Obj("3"),
                new Obj("4"),
                new Obj("5")
        };

        for (Obj obj : objs) {
            nodeArray.put(obj);
        }

        validate(nodeArray, objs);

    }

    private static void validate(NodeArray nodeArray, Obj[] objs) {
        for (Obj obj : objs) {
            System.out.println(nodeArray.get(obj));
        }

        nodeArray.addNode(new Node("anything1"));
        nodeArray.addNode(new Node("anything2"));

        System.out.println("========== after  =============");

        for (Obj obj : objs) {
            System.out.println(nodeArray.get(obj));
        }
    }





    static class NodeArray {

        /** 按照 键 排序*/
        TreeMap<Integer, Node> nodes = new TreeMap<>();

        void addNode(Node node) {
            nodes.put(node.hashCode(), node);
        }

        void put(Obj obj) {
            int objHashcode = obj.hashCode();
            Node node = nodes.get(objHashcode);
            if (node != null) {
                node.putObj(obj);
                return;
            }

            // 找到比给定 key 大的集合
            SortedMap<Integer, Node> tailMap = nodes.tailMap(objHashcode);
            // 找到最小的节点
            int nodeHashcode = tailMap.isEmpty() ? nodes.firstKey() : tailMap.firstKey();
            nodes.get(nodeHashcode).putObj(obj);

        }

        Obj get(Obj obj) {
            Node node = nodes.get(obj.hashCode());
            if (node != null) {
                return node.getObj(obj);
            }

            // 找到比给定 key 大的集合
            SortedMap<Integer, Node> tailMap = nodes.tailMap(obj.hashCode());
            // 找到最小的节点
            int nodeHashcode = tailMap.isEmpty() ? nodes.firstKey() : tailMap.firstKey();
            return nodes.get(nodeHashcode).getObj(obj);
        }
    }

}






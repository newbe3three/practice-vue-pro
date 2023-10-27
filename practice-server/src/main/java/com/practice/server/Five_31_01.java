package com.practice.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class Five_31_01 {

    public static void main(String args[]){
        int nums[] = {1, 3, 2};
        Five_31_01 t = new Five_31_01();
        t.nextPermutation(nums);
    }

    public void nextPermutation(int[] nums) {
        //求出序列的全排列
        List<List<Integer>> res = permute(nums);

        System.out.println(res.toString());
        //将当前全排列转换成数字
        int current = arrayConvertoNum(nums);
        System.out.println(current);
        //将全排列全部转成从数字存在数组中
        int[] A = convertoNums(res);
        System.out.println(Arrays.toString(A));
        //找到下一个排列对应的数
        int num = findNextNum(A,current);
        if(num > 0){
            //讲数字转换为数组
            nums = convertoArray(num, nums.length - 1);
        }
        System.out.println(Arrays.toString(nums));
    }

    public int findNextNum(int[] A, int current){
        int num = Integer.MAX_VALUE;
        int pre = 0;
        int k = 0;
        for(int i = 0; i < A.length; i++){
            if(A[i] - current > 0){
                pre = A[i] - current;
                if(pre < num){
                    num = pre;
                    k = i;
                }
            }
        }
        if(pre > 0){
            return A[k];
        }else{
            return 0;
        }
    }

    public int[] convertoArray(int num, int len){
        int[] res = new int[len+1];
        int i = len;
        while(i >= 0){
            res[i] = num % 10;
            num = num / 10;
            i--;
        }
        return res;
    }

    // 将当前序列转换成数字
    public int arrayConvertoNum(int[] nums){
        int len = nums.length - 1;
        int num = 0;
        int i = 0;
        while(i <= len){
            num = num * 10 + nums[i];
            i++;
        }
        return num;
    }

    // 将当前序列转换成数字
    public int listConvertoNum(List<Integer> list){
        int len = list.size() - 1;
        int num = 0;
        int i = 0;
        while(i <= len){
            num = num * 10 + list.get(i);
            i++;
        }
        return num;
    }

    // 将所有的全排列转换成数字
    public int[] convertoNums(List<List<Integer>> res){
        int B[] = new int[res.size()];
        for(int i = 0; i < res.size(); i++){
            B[i] = listConvertoNum(res.get(i));
        }
        return B;
    }

    // 回溯法
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();//存放全排列结果
        boolean[] used = new boolean[nums.length];//用于标记位置是否被访问
        backtrack(ans, nums, used, new ArrayList<>());
        return ans;
    }
    public void backtrack(List<List<Integer>> ans, int[] nums, boolean[] used, List<Integer> list) {
        if (list.size() == nums.length) {//当长度等于序列的长度时，放入结果中
            ans.add(new ArrayList<>(list));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) continue;//如果当前元素被访问过，跳到下一个循环，寻找下一个数
            list.add(nums[i]);//未被添加进列表，添加
            used[i] = true;//已被添加，标记为已经访问
            backtrack(ans, nums, used, list);//添加下一个元素
            list.remove(list.size() - 1);//将上一个元素从列表移除
            used[i] = false;//修改标志位为未访问
        }
    }
}

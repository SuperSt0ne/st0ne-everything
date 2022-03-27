package com.rango.leetcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 给定一个整数数组 nums 和一个整数目标值 target，请你在该数组中找出 和为目标值 target  的那 两个 整数，并返回它们的数组下标。
 *
 * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素在答案里不能重复出现。
 *
 * 你可以按任意顺序返回答案。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/two-sumo
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 */
public class TwoSum {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(twoSum(new int[]{3, 5, 4, 1, 2, 9}, 9)));
    }

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> val4IndexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int val = target - nums[i];
            if (val4IndexMap.containsKey(val)) {
                return new int[] {val4IndexMap.get(val), i};
            }
            val4IndexMap.put(nums[i], i);
        }
        return new int[] {};
    }
}

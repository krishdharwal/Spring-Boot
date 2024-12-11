package movies;

import java.util.ArrayList;
import java.util.List;

public class experiment {



        public static int minimumSumSubarray(List<Integer> nums, int l, int r) {
            int sum = 0;
            int i = 0;

            int s = 0;
            int ls = 1000;
            int rs = 1000;

            while(l < nums.size()){
                int k = sum(nums, s ,l);
                if(k > 0 && k < ls){
                    ls = k;
                }
                s++;
                l++;
            }

            s = 0;
            while(r < nums.size()){
                int k = sum(nums, s ,r);
                if(k > 0 &&  k < ls){
                    rs = k;
                }
                s++;
                r++;
            }

            int min = Math.min(ls,rs);
            if(min <= 0) return -1;
            return min;

        }

        public static int sum(List<Integer> nums, int s, int e) {

            int sum = 0;

            for(int i = s; i < e; i++ ){
                sum += nums.get(i);
            }
            return sum;
        }



    public static void main(String[] args) {
       int ans = minimumSumSubarray(List.of(1,2,3,4), 2 , 4);
        System.out.println(ans);

    }

}

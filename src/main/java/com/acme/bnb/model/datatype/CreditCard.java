package com.acme.bnb.model.datatype;

import com.acme.bnb.controlers.clases.Validable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreditCard implements Validable {

    @Pattern(regexp = "^[0-9]*$", message = "Invalid creditcard number")
    @Column(length = 20)
    private String number;
    
    @Min(1)
    @Max(12)
    @Column(nullable = true)
    private int expiracyMonth;
    
    @Column(nullable = true)
    private int expiracyYear;
    
    @Column(length = 3)
    private String cvv;

    @JsonIgnore
    @Override
    public boolean isValid() {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        int nowMonth = now.get(Calendar.MONTH);
        int nowYear = now.get(Calendar.YEAR);
        
        if(now.get(Calendar.DAY_OF_MONTH) >= YearMonth.of(nowYear, nowMonth).lengthOfMonth()-7){
            now.add(Calendar.MONTH, 1);
            nowMonth = now.get(Calendar.MONTH);
            nowYear = now.get(Calendar.YEAR);
        }
        
        return !(StringUtils.isBlank(number) || StringUtils.isBlank(cvv) || number.length() < 15 || !luhnCheck(number) || cvv.length() != 3 || expiracyYear < nowYear || (expiracyYear == nowYear && expiracyMonth < nowMonth));
    }
    
    public void hide(){
        cvv = "XXX";
        if(number != null){
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < number.length(); i++) {
                if(i <= 3 || i >= number.length()-4)
                    sb.append(number.charAt(i));
                else
                    sb.append('X');
            }
            number = sb.toString();
        }
    }
    
    private static boolean luhnCheck(String number){
        try{
            List<Integer> ints = number.chars().map(c -> Integer.parseInt(Character.toString(c))).boxed().collect(Collectors.toList());
            int sum = IntStream.range(0, number.length()).filter(i -> i%2 == 0).map(i -> ints.get(i)*2).map(n -> n >= 10 ? ((n%10)+(n/10)) : n).sum();
            sum += IntStream.range(0, number.length()).filter(i -> i%2 != 0).map(i -> ints.get(i)).sum();
            return sum %10 == 0;
        }catch(Exception e){
            return false;
        }
    }
}

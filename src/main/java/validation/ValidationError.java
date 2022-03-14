package validation;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationError {
    @NonNull
    private String code;

    private List<String> arguments;
}



//package validation;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//
//import javax.validation.constraints.NotNull;
//import java.util.List;
//
//@Getter
////@AllArgsConstructor
//@RequiredArgsConstructor
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class ValidationError {
//    @NotNull
//    private String code;
//
//    private List<String> arguments;
//
//    public ValidationError (String codes, List<String> args){
//
//    }
//
//    @Override
//    public String toString() {
//        return "ValidationError{" +
//                "code='" + code + '\'' +
//                ", arguments=" + arguments +
//                '}';
//    }
//}
//
//
////
////    public String getCode() {
////        return code;
////    }
////
////    public void setCode(String code) {
////        this.code = code;
////    }
////
////    public List<String> getArguments() {
////        return arguments;
////    }
////
////    public void setArguments(List<String> arguments) {
////        this.arguments = arguments;
////    }

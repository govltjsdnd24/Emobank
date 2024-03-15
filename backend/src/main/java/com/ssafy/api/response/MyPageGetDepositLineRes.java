package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.join.GetDeposit;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class MyPageGetDepositLineRes extends BaseResponseBody {
    List<GetDeposit> data;

    public static MyPageGetDepositLineRes of(int statusCode, String message, List<GetDeposit> data) {
        MyPageGetDepositLineRes res = new MyPageGetDepositLineRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setData(data);

        return res;
    }
}

package com.springboot.environment.util;

import com.springboot.environment.service.WarningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Created by sts on 2018/12/30.
 */

@Component
public class GetWarningLastNum implements CommandLineRunner{


    @Autowired
    private WarningService warningService;

    public static int LastWarningNum;

    public static void setLastWarningNum(int newWarningNum) { LastWarningNum = newWarningNum; }
    public int getLastWarningNum() { return LastWarningNum; }

    @Override
    public void run(String... strings) throws Exception {

        LastWarningNum = warningService.getCount();
        System.out.print(LastWarningNum);
    }
}

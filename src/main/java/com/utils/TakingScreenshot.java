package com.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.google.common.io.Files;

import com.base.TestBase;

public class TakingScreenshot extends TestBase
{
	public static String getScreenshot(String methodname) {
		TakesScreenshot ss=(TakesScreenshot) driver;
		File src = ss.getScreenshotAs(OutputType.FILE);
		String path=System.getProperty("user.dir")+"/Screenshots/"+methodname+".png";
		File dest=new File(path);
		try {
			FileUtils.copyFile(src, dest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}
}

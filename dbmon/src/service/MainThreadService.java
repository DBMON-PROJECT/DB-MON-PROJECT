package service;

import common.ConstantCommon;
import controller.RealtimeController;
import javafx.application.Platform;

/**
 * 
* 1. 패키지명 : service
* 2. 타입명 : MainThreadService.java
* 3. 작성일 : 2015. 8. 15. 오후 3:37:21
* 4. 작성자 : 길용현
* 5. 설명 : chart 구현을 위한 Thread 클래스
 */
public class MainThreadService extends Thread{
	private boolean stop ;
	private boolean suspend ;
	private RealtimeService realtimeService = RealtimeService.getInstance();
	private boolean soundBol = false;
	
	public MainThreadService(){
		stop = true;
		suspend = true;
	}
	
	public void run() {
		while(stop){
			if(suspend){
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						// chart initialize
						if(ConstantCommon.cnt==0){
							RealtimeController.realtimeController.initAllData();
						}
						
						if(ConstantCommon.cnt >= 15){
							// chart data add
							soundBol = realtimeService.addData(ConstantCommon.cnt);
							
							if(soundBol){
								realtimeService.playSound();
							}
						}else{
							realtimeService.addLoadingData(ConstantCommon.cnt);
						}
					}
				});
			}
			
			try {
				if(ConstantCommon.cnt >= 15){
					Thread.sleep(3000);
					
					if(soundBol){
						realtimeService.stopSound();
					}
				}else{
					Thread.sleep(100);
				}
			} catch (InterruptedException ex) {
				break;
			}
			
			if(suspend){
				ConstantCommon.cnt++;
			}
		}
	}
	
	public void stopThread(){
		stop = false;
	}
	
	// thread sleep
	public void suspendThread(){
		suspend = false;
	}
	
	// thread awake
	public void resumeThread(){
		suspend = true;
	}
}

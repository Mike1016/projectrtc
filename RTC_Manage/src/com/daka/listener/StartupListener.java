package com.daka.listener;

import com.daka.queue.QueueUtil;
import com.daka.service.customer.CustomerTeamService;
import com.daka.service.dictionaries.DictionariesService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private DictionariesService dictionariesService;

    @Autowired
    private CustomerTeamService customerTeamService;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
			dictionariesService.init();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					customerTeamService.takeQueue();					
				}
			}).start();
        }
    }
}

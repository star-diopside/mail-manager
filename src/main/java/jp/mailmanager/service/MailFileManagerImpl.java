package jp.mailmanager.service;

import org.springframework.stereotype.Service;

@Service
public class MailFileManagerImpl implements MailFileManager {

    @Override
    public void execute() {
        System.out.println("execute");
    }
}

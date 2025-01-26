package org.eu.hanana.reimu.lib.satori.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eu.hanana.reimu.lib.satori.util.StringUtil;
import org.eu.hanana.reimu.lib.satori.v1.client.AuthenticatorC;
import org.eu.hanana.reimu.lib.satori.v1.client.SatoriClient;
import org.eu.hanana.reimu.lib.satori.v1.protocol.SignalBodyIdentify;

public class ClientMain implements Runnable{
    private static final Logger log = LogManager.getLogger(ClientMain.class);
    public SatoriClient client;
    public static ClientMain Instance;
    public static String[] args;
    public static void main(String[] args) {
        log.info("Test client starting.");
        Thread.currentThread().setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.fatal(StringUtil.getFullErrorMessage(e));
            }
        });
        ClientMain.args=args;
        Instance=new ClientMain();
        Instance.run();
    }

    @Override
    public void run() {
        client=SatoriClient.createSatoriClient(args[0]);
        client.addAuthenticator(new AuthenticatorC(new SignalBodyIdentify(args[1])));
        client.open();
    }
}
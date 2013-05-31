/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package org.apache.shiro.session.mgt.eis;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.shiro.session.mgt.OnlineSession;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-3-20 下午10:01
 * <p>Version: 1.0
 */
public class OnlineSessionTest {

    @Test
    public void testSerialize() throws IOException, ClassNotFoundException, DecoderException {
        OnlineSession session = new OnlineSession();

        session.setId(123);
        session.setHost("127.0.0.1");
        session.setTimeout(1);
        session.setUserId(123L);
        session.setAttribute("z", "z");
        session.setStatus(OnlineSession.OnlineStatus.force_logout);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(session);
        oos.close();
        bos.close();
        byte[] objectBytes = bos.toByteArray();

        String str = Hex.encodeHexString(objectBytes);

        ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(Hex.decodeHex(str.toCharArray())));

        OnlineSession actualSession = (OnlineSession) ois.readObject();
        ois.close();

        Assert.assertEquals(session.getId(), actualSession.getId());
        Assert.assertEquals(session.getHost(), actualSession.getHost());
        Assert.assertEquals(session.getTimeout(), actualSession.getTimeout());
        Assert.assertEquals(session.getUserId(), actualSession.getUserId());
        Assert.assertEquals(session.getAttributes().get("z"), actualSession.getAttributes().get("z"));
        Assert.assertEquals(session.getStatus(), actualSession.getStatus());
        Assert.assertEquals(session.getSystemHost(), actualSession.getSystemHost());


    }
}

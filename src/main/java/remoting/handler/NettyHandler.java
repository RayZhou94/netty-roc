/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package remoting.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import utils.AddressUtil;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * NettyHandler
 */
@Slf4j
public class NettyHandler extends ChannelInboundHandlerAdapter {

    private AbstractInvocationHandler handler;

    private final Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    public NettyHandler(AbstractInvocationHandler handler){
        this.handler = handler;
    }

    public Map<String, Channel> getChannels(){
        return channelMap;
    }

    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("{} connected", handler.getClass().getSimpleName());
        Channel channel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        channelMap.put(AddressUtil.buildAddress(address), channel);
        handler.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();
        channelMap.remove(AddressUtil.buildAddress(address));
        handler.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        handler.channelRead(ctx, msg);
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelReadComplete();
    }

}
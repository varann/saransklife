package ru.saransklife.client;

import org.androidannotations.annotations.EBean;

/**
 * Created by asavinova on 06/11/14.
 */
@EBean(scope = EBean.Scope.Singleton)
public class EventBus extends de.greenrobot.event.EventBus {
}

package com.soonphe.timber.ui.game.game;

import com.soonphe.timber.view.entity.TAdvert;
import com.soonphe.timber.view.entity.TGame;
import com.soonphe.timber.view.entity.TGameType;
import com.soonphe.timber.base.mvp.BasePView;

import java.util.List;


public class GameContract {
    interface View extends BasePView {
        void getAdvertListSuccess(List<TAdvert> list );
        void getTypeListSuccess(List<TGameType> list );
        void getGameListSuccess( List<TGame> list);
    }

    interface  Presenter {
        void getAdvertList( );
        void getTypeList( );
        void getGameList( int gameid);
    }
}

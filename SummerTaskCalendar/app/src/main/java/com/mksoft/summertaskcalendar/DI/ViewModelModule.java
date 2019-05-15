package com.mksoft.summertaskcalendar.DI;



import com.mksoft.summertaskcalendar.ViewModel.FactoryViewModel;
import com.mksoft.summertaskcalendar.ViewModel.MemoViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MemoViewModel.class)
    abstract ViewModel bindMemoDataViewModel(MemoViewModel memoViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}

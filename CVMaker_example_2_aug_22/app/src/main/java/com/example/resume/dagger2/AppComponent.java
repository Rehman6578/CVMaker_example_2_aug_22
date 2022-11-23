package com.example.resume.dagger2;

import com.example.resume.fragments.CVDataListFragment;
import com.example.resume.fragments.infogathering.AddImageFragment;
import com.example.resume.pdfscanner.patterns.GeneratorPDF;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void inject(CVDataListFragment fragment);

    void inject(AddImageFragment fragment);

    void inject(GeneratorPDF generatorPDF);
}

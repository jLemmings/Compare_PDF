import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';


import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HeaderComponent} from './header/header.component';
import {MainComponent} from './main/main.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
    MatButtonModule,
    MatFormFieldModule,
    MatGridListModule,
    MatInputModule,
    MatSlideToggleModule,
    MatToolbarModule,
    MatListModule, MatIconModule
} from '@angular/material';
import {PdfViewerModule} from 'ng2-pdf-viewer';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {FlexLayoutModule} from '@angular/flex-layout';
import {DraftableComponent} from './draftable/draftable.component';
import {PdfcompareComponent} from './pdfcompare/pdfcompare.component';
import {ComparejobdataService} from './shared/comparejobdata.service';
import {HttpClientModule} from '@angular/common/http';



@NgModule({
    declarations: [
        AppComponent,
        HeaderComponent,
        MainComponent,
        DraftableComponent,
        PdfcompareComponent
    ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        BrowserAnimationsModule,
        MatToolbarModule,
        PdfViewerModule,
        FormsModule,
        MatGridListModule,
        MatButtonModule,
        MatFormFieldModule,
        MatInputModule,
        MatSlideToggleModule,
        MatListModule,
        FlexLayoutModule,
        MatIconModule,
        HttpClientModule,
        ReactiveFormsModule

    ],
    providers: [ComparejobdataService
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}

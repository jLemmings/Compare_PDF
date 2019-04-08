import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {MainComponent} from './main/main.component';
import {DraftableComponent} from './draftable/draftable.component';
import {PdfcompareComponent} from './pdfcompare/pdfcompare.component';

const routes: Routes = [
    {path: 'main', component: MainComponent},
    {path: 'draftable', component: DraftableComponent},
    {path: 'pdfcompare', component: PdfcompareComponent},
    {path: '**', component: MainComponent},

];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}

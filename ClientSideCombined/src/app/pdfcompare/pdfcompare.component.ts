import { Component, OnInit } from '@angular/core';
import {ComparejobdataService} from '../shared/comparejobdata.service';

@Component({
  selector: 'app-pdfcompare',
  templateUrl: './pdfcompare.component.html',
  styleUrls: ['./pdfcompare.component.css']
})
export class PdfcompareComponent implements OnInit {


  constructor(private comparejobdataService: ComparejobdataService) {

  }

  ngOnInit() {
    console.log('UiIntArray: ', this.comparejobdataService.pdfFile);
  }


}

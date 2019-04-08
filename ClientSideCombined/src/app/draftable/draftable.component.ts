import { Component, OnInit } from '@angular/core';
import {DomSanitizer, SafeUrl} from '@angular/platform-browser';
import {ComparejobdataService} from '../shared/comparejobdata.service';

@Component({
  selector: 'app-draftable',
  templateUrl: './draftable.component.html',
  styleUrls: ['./draftable.component.css']
})
export class DraftableComponent implements OnInit {

  url: string | SafeUrl = '';


  constructor(public sanitizer: DomSanitizer, private comparejobdataService: ComparejobdataService) { }

  ngOnInit() {
    this.url = this.getSafeUrl(this.comparejobdataService.url);

  }

  getSafeUrl(url) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(url);
  }

}

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PdfcompareComponent } from './pdfcompare.component';

describe('PdfcompareComponent', () => {
  let component: PdfcompareComponent;
  let fixture: ComponentFixture<PdfcompareComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PdfcompareComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PdfcompareComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

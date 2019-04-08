import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DraftableComponent } from './draftable.component';

describe('DraftableComponent', () => {
  let component: DraftableComponent;
  let fixture: ComponentFixture<DraftableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DraftableComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DraftableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

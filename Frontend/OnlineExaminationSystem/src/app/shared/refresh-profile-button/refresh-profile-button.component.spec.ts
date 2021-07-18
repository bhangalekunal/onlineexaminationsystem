import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RefreshProfileButtonComponent } from './refresh-profile-button.component';

describe('RefreshProfileButtonComponent', () => {
  let component: RefreshProfileButtonComponent;
  let fixture: ComponentFixture<RefreshProfileButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RefreshProfileButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(RefreshProfileButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

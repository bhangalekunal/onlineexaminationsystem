import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateProfileButtonComponent } from './create-profile-button.component';

describe('CreateProfileButtonComponent', () => {
  let component: CreateProfileButtonComponent;
  let fixture: ComponentFixture<CreateProfileButtonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CreateProfileButtonComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(CreateProfileButtonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

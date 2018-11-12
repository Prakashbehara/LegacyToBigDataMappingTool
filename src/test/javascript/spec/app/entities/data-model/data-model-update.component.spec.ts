/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { DataModelUpdateComponent } from 'app/entities/data-model/data-model-update.component';
import { DataModelService } from 'app/entities/data-model/data-model.service';
import { DataModel } from 'app/shared/model/data-model.model';

describe('Component Tests', () => {
    describe('DataModel Management Update Component', () => {
        let comp: DataModelUpdateComponent;
        let fixture: ComponentFixture<DataModelUpdateComponent>;
        let service: DataModelService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelUpdateComponent]
            })
                .overrideTemplate(DataModelUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataModelUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataModelService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataModel(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataModel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataModel();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataModel = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});

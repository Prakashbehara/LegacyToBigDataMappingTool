/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { DataModelMappingUpdateComponent } from 'app/entities/data-model-mapping/data-model-mapping-update.component';
import { DataModelMappingService } from 'app/entities/data-model-mapping/data-model-mapping.service';
import { DataModelMapping } from 'app/shared/model/data-model-mapping.model';

describe('Component Tests', () => {
    describe('DataModelMapping Management Update Component', () => {
        let comp: DataModelMappingUpdateComponent;
        let fixture: ComponentFixture<DataModelMappingUpdateComponent>;
        let service: DataModelMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [DataModelMappingUpdateComponent]
            })
                .overrideTemplate(DataModelMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DataModelMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DataModelMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DataModelMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataModelMapping = entity;
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
                    const entity = new DataModelMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.dataModelMapping = entity;
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

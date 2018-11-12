/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { MyAppTestModule } from '../../../test.module';
import { SourceMappingUpdateComponent } from 'app/entities/source-mapping/source-mapping-update.component';
import { SourceMappingService } from 'app/entities/source-mapping/source-mapping.service';
import { SourceMapping } from 'app/shared/model/source-mapping.model';

describe('Component Tests', () => {
    describe('SourceMapping Management Update Component', () => {
        let comp: SourceMappingUpdateComponent;
        let fixture: ComponentFixture<SourceMappingUpdateComponent>;
        let service: SourceMappingService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [MyAppTestModule],
                declarations: [SourceMappingUpdateComponent]
            })
                .overrideTemplate(SourceMappingUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SourceMappingUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SourceMappingService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SourceMapping(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceMapping = entity;
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
                    const entity = new SourceMapping();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sourceMapping = entity;
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
